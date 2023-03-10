package com.project.ConferenceManagement.controller;

import java.io.File;
import java.io.FileOutputStream;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.model.EvaluationModel;
import com.project.ConferenceManagement.model.UserResponse;
import com.project.ConferenceManagement.repository.UserRepository;
import com.project.ConferenceManagement.service.EvaluationService;

@RestController
public class EvaluationController {

	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	EvaluationService evaluationServiceImpl;
	
	 @PostMapping("/upload") 
	  public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file,@RequestParam("who") String who ) {
		 String path=null;
		 if(who.equals("author")) {
			path="src\\main\\resources\\static\\upfiles\\author\\";
		}
		 else{
			 path="src\\main\\resources\\static\\upfiles\\ref\\";
		 }
	    String fileName = file.getOriginalFilename();
	    File pathFile= new File(path);
	    try {    	
		    if(!pathFile.exists()) {
		    	pathFile.mkdir();
		    }
	    	 File convertFile = new File(path+fileName);
	         convertFile.createNewFile();
	         FileOutputStream fout = new FileOutputStream(convertFile);
	         fout.write(file.getBytes());
	         fout.close();	
	    } catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    } 
	    return ResponseEntity.ok("File uploaded successfully.");
	  }
	 @PostMapping("/toApply")
	 public String toApply(@RequestBody EvaluationModel evaluationModel) {
		 String ret="";
		 try {
			UserEntity user= userRepository.findByEmail(evaluationModel.getEmail());
			if(user!=null) {
				evaluationModel.setUser(user);
				ret=evaluationServiceImpl.toApply(evaluationModel);
			}
		} catch (IllegalArgumentException e) {
			ret="Failed";
		}
		 return ret;
	 }
	 @PostMapping("/getEvaluationTable")
	 public List<EvaluationModel> getEvaluationTable(@RequestBody UserResponse userRes){
		UserEntity user= userRepository.findByEmail(userRes.getEmail());
		return evaluationServiceImpl.getEvaluationTable(user);
	 }
	 
	 @PostMapping("/getEvaluationTableForAll")
	 public List<EvaluationModel> getEvaluationTableForRef(){
		return evaluationServiceImpl.getEvaluationTableForAll();
	 }	 
	 @PostMapping("/changeStatusEvaByOKB")
	 public Boolean changeStatusEvaByOKB(@RequestBody EvaluationModel evaluationModel){
		return evaluationServiceImpl.changeStatusEvaByOKB(evaluationModel.getId());
	 }	 
	 
	 @PostMapping("/setEvulationFinishByOKB")
	 public Boolean setEvulationFinishByOKB(@RequestBody EvaluationModel evaluationModel){
		return evaluationServiceImpl.setEvulationFinishByOKB(evaluationModel);
	 }	 
	 
}
	  
