1. 安装
java (>=8)
maven

2. 点击根目录run.bat，然后充分的等待

3. 访问 http://localhost:8081/getName 然后不断的刷新页面，可以看到同一个api请求，被不同的服务器提供了服务
即使其中一个服务器当机，这个api还是能正常运作，因为还有一个服务器正常运行。


解释：
RegistryServer 这个是Eureka的维护服务器列表的一个地方，可访问http://localhost:8761查看当前运行的服务器
代码：
```
@SpringBootApplication
@EnableEurekaServer
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
```

Service1，Service2 是两个服务器，服务名叫eurekaclient
Service1代码：
```
@SpringBootApplication
@EnableEurekaClient
@RestController
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
   @RequestMapping(value = "/")
   public String home() {
      return "In service1";
   }
}
```

Service2代码：
```
@SpringBootApplication
@EnableEurekaClient
@RestController
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
   @RequestMapping(value = "/")
   public String home() {
      return "In service2";
   }
}
```

ApplicationServer 这个是调用的地方，访问 http://localhost:8081/getName，会被Service1，Service2 轮流响应，即使其中一个坏掉，也可以正常访问http://localhost:8081/getName
代码：
```
@Autowired
RestTemplate restTemplate;

@RequestMapping(value = "/getName", method = RequestMethod.GET)
public String getStudents() 
{
	String response = restTemplate.exchange("http://eurekaclient/",
							HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, "").getBody();
	System.out.println("Response Received as " + response);
	return "served by: " + response;
}

@Bean
@LoadBalanced
public RestTemplate restTemplate() {
	return new RestTemplate();
}
```






















