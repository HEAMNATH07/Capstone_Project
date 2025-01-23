package com.example.Task.Model;

import com.example.Task.Enum.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Task { 
	
	@Id
	private String id; 
	private String name; 
	private String description; 
	TaskStatus status;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "dd-MM-yyyy hh:mm")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy hh:mm")
	private Date startDateTime, endDateTime;
	
	private String eventId; 
}