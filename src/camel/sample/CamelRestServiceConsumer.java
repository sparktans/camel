package camel.sample;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.BasicConfigurator;

public class CamelRestServiceConsumer {
	
	public static void main(String[] args) {
		
//		to enable log messages for execution
		BasicConfigurator.configure(); 
//		to use proxy for accessing RSS feed
		System.setProperty("http.proxyHost","proxy.gbs.pro");
		System.setProperty("http.proxyPort","8080");
		System.setProperty("https.proxyHost","proxy.gbs.pro");
		System.setProperty("https.proxyPort","8080"); 

		final CamelContext camelContext = new DefaultCamelContext();
		
		try {
			
			camelContext.addRoutes(new RouteBuilder() {
				
				@Override
				public void configure() throws Exception {
					

			        // START SNIPPET: consumer_route
			        from("restlet:http://34.196.163.71:8080/biot/rest/notifications/GBS00003/ae9f535968375182be8247f6c18d195bc77d951f3bda52cbd27c730e8899ec76436218a272dc8f27/notification/0").
/*			        	process(new Processor() {
			            public void process(Exchange exchange) throws Exception {
			               String message = exchange.getOut().getBody(String.class);
			               System.out.println("########## OUTPUT FROM SELLA CONNECT SERVICE :"+message+":#######");
			               exchange.getOut().setBody("DUMMY OUTPUT SET");
			            }
			        }).
*/			        to("kafka:localhost:9092?topic=my-failsafe-topic&serializerClass=kafka.serializer.StringEncoder");
//					to("kafka:nbkps133:9092?topic=my-failsafe-topic&serializerClass=kafka.serializer.StringEncoder");
					
					
				}
			});
			
			System.out.println("#################### Starting the context");
			camelContext.start();
			Thread.sleep(100000);
			System.out.println("#################### stopping the context");
			camelContext.stop();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
