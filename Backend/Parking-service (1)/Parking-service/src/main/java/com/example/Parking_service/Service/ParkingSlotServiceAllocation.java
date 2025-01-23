package com.example.Parking_service.Service;

import com.example.Parking_service.Model.ParkingSlot;
import com.example.Parking_service.Repository.ParkingSlotRepository;
import com.example.Parking_service.Response.MessageResponse;
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
public class ParkingSlotServiceAllocation {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<?> allocateParkingSlot(Long id, String email) {
        if (Objects.isNull(id) || Objects.isNull(email) || email.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Invalid parking slot ID or email", HttpStatus.BAD_REQUEST.value()));
        }

        Optional<ParkingSlot> optionalParkingSlot = parkingSlotRepository.findById(id);
        if (!optionalParkingSlot.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Parking slot not found", HttpStatus.NOT_FOUND.value()));
        }

        ParkingSlot parkingSlot = optionalParkingSlot.get();
        if (!parkingSlot.isAvailable()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Parking slot already allocated", HttpStatus.CONFLICT.value()));
        }

        parkingSlot.setAvailable(false);
        parkingSlot.setEmail(email);
        parkingSlotRepository.save(parkingSlot);

        try {
            // Generate QR Code
            String qrData = "Parking Slot ID: " + parkingSlot.getId() + ", Name: " + parkingSlot.getId();
            byte[] qrCode = generateQRCode(qrData);

            // Send Email with QR Code
            sendParkingSlotAllocationEmail(parkingSlot, qrCode);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error during parking slot allocation process", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Parking slot allocated successfully and email sent", HttpStatus.OK.value()));
    }

    private byte[] generateQRCode(String data) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }

    private void sendParkingSlotAllocationEmail(ParkingSlot parkingSlot, byte[] qrCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(parkingSlot.getEmail());
        helper.setSubject("Parking Slot Allocation Confirmation");
        helper.setText("Dear User,\n\n"
                + "Your parking slot \"" + parkingSlot.getEmail() + "\" at \"" + parkingSlot.getSlotNumber() + "\" has been successfully allocated.\n"
                + "Please find the attached QR code for your records.\n\n"
                + "Best regards,\nParking Management Team");

        ByteArrayDataSource qrCodeAttachment = new ByteArrayDataSource(qrCode, "image/png");
        helper.addAttachment("ParkingSlotQR.png", qrCodeAttachment);

        javaMailSender.send(mimeMessage);
    }

    public ResponseEntity<?> releaseParkingSlot(Long id) {
        if (Objects.isNull(id)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Invalid parking slot ID", HttpStatus.BAD_REQUEST.value()));
        }

        Optional<ParkingSlot> optionalParkingSlot = parkingSlotRepository.findById(id);
        if (!optionalParkingSlot.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Parking slot not found", HttpStatus.NOT_FOUND.value()));
        }

        ParkingSlot parkingSlot = optionalParkingSlot.get();
        parkingSlot.setAvailable(true);
        parkingSlotRepository.save(parkingSlot);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Parking slot released successfully", HttpStatus.OK.value()));
    }
}