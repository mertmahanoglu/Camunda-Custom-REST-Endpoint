# Camunda-Custom-REST-Endpoint

Spring Boot, Maven, Jersey kullanılarak oluşturulmuştur.

Oluşturulan bu demo proje, Camunda BPMN Engine üzerinde bulunan REST endpointları dışında custom bir endpoint oluturmak ve Camunda engine (engine-rest) üzerinden istek atarak içerisinde bulunan işlemleri gerçekleştirmektedir.

# Nasıl Çalışır?

 CustomRestService sınıfı @Component ile işaretlenir ve engine ayağa kalkarken taranır ve path bilgisi @Path ile verilir. Metotların path ve istek bilgileri tanımlanır.
  
  ```Java
  @Component
@Path("/testEndpoint")
public class CustomRestService {

  public static final String PD_KEY = "testEndpoint";
  @Autowired
  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

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
  ```
  ResourceConfigCustomizer ile oluşturulan sınıf register edilir.
  
```Java
    @Component
    public class MyResourceConfigCustomizer extends CustomRestService implements ResourceConfigCustomizer {

	 @Override
	  public void customize(ResourceConfig config) {
	    config.register(CustomRestService.class);
	  }

}
    
 ```
Yazılan custom servis engine ile aynı package içerisinde bulunmadığından engine içerisinde ComponentScan ile taranır.

```Java

@SpringBootApplication
@ComponentScan(basePackages = "org.camunda")
public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }

}

```
  
