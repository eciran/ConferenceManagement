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
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="assignment")
public class AssignmentEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="evaluation_id",
	nullable = false,
	foreignKey = @ForeignKey(name="FK_EVALUATION_ID"))
	private EvaluationEntity evaluation;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id",
	nullable = false,
	foreignKey = @ForeignKey(name="FK_REF_ID"))
	private UserEntity referee;
	private boolean status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EvaluationEntity getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(EvaluationEntity evaluation) {
		this.evaluation = evaluation;
	}
	public UserEntity getReferee() {
		return referee;
	}
	public void setReferee(UserEntity referee) {
		this.referee = referee;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
