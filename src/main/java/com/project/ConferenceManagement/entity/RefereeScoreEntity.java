package com.project.ConferenceManagement.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="refere_score")
public class RefereeScoreEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="assignment_id",
	nullable = false,
	foreignKey = @ForeignKey(name="FK_ASSIGNMENT_ID"))
	private AssignmentEntity assignmentId;
	private String description;
	private String score;
	private String scoreFilePath;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public AssignmentEntity getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(AssignmentEntity assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getScoreFilePath() {
		return scoreFilePath;
	}
	public void setScoreFilePath(String scoreFilePath) {
		this.scoreFilePath = scoreFilePath;
	}
	
	
}
