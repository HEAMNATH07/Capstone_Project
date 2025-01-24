package com.example.WorkSpace.Service;

import com.example.WorkSpace.Model.Workspace;
import com.example.WorkSpace.Repository.WorkspaceRepository;
import com.example.WorkSpace.Response.MessageResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.Optional;

@Service
public class WorkspaceServiceAllocation {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<?> allocateWorkspace(Long id, String email) {
        if (Objects.isNull(id) || Objects.isNull(email) || email.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Invalid workspace ID or email", HttpStatus.BAD_REQUEST.value()));
        }

        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Workspace not found", HttpStatus.NOT_FOUND.value()));
        }

        Workspace workspace = optionalWorkspace.get();
        if (!workspace.isAvailable()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Workspace already allocated", HttpStatus.CONFLICT.value()));
        }

        workspace.setAvailable(false);
        workspace.setEmail(email);
        workspaceRepository.save(workspace);

        try {
            // Generate QR Code
            String qrData = "Workspace ID: " + workspace.getId() + ", Name: " + workspace.getName();
            byte[] qrCode = generateQRCode(qrData);

            // Send Email with QR Code
            sendWorkspaceAllocationEmail(workspace, qrCode);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error during workspace allocation process", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Workspace allocated successfully and email sent", HttpStatus.OK.value()));
    }

    private byte[] generateQRCode(String data) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }

    private void sendWorkspaceAllocationEmail(Workspace workspace, byte[] qrCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(workspace.getEmail());
        helper.setSubject("Workspace Allocation Confirmation");
        helper.setText("Dear User,\n\n"
                + "Your workspace \"" + workspace.getName() + "\" at \"" + workspace.getLocation() + "\" has been successfully allocated.\n"
                + "Please find the attached QR code for your records.\n\n"
                + "Best regards,\nWorkspace Management Team");

        ByteArrayDataSource qrCodeAttachment = new ByteArrayDataSource(qrCode, "image/png");
        helper.addAttachment("WorkspaceQR.png", qrCodeAttachment);

        javaMailSender.send(mimeMessage);
    }
    public ResponseEntity<?> releaseWorkspace(Long id) {
        if (Objects.isNull(id)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Invalid workspace ID", HttpStatus.BAD_REQUEST.value()));
        }

        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Workspace not found", HttpStatus.NOT_FOUND.value()));
        }

        Workspace workspace = optionalWorkspace.get();
        workspace.setAvailable(true);
        workspaceRepository.save(workspace);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Workspace released successfully", HttpStatus.OK.value()));
    }
}

