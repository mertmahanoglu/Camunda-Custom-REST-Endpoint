package org.camunda.apiendpoints;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import camundajar.impl.com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Component
@Path("/testEndpoint")
public class CustomRestService {

  public static final String PD_KEY = "testEndpoint";
  @Autowired
  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

  @GET
  @Path("/getList")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getList() {
	 Task task =processEngine.getTaskService().createTaskQuery().processDefinitionKey("camunda-engine-process").orderByTaskCreateTime().desc().list().get(0);
		
	    return Response.status(201)
	        .entity(task.getProcessDefinitionId()).build();
  }

  @POST
  @Path("/start/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response startProcess() {
    var pi = processEngine.getRuntimeService().startProcessInstanceByKey(PD_KEY);
    return Response.status(201)
        .entity(ProcessInstanceDto.fromProcessInstance(pi)).build();
  }
  
  
  @GET
  @Path("/GetUsers/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response GetUsers() {
    var users = processEngine.getIdentityService().createUserQuery().list();
    String jsonString = new Gson().toJson(users);
    return Response.status(201)
	        .entity(jsonString).build();
  }
  
  
  
  
  
  
  
  
  
  
  
  
}