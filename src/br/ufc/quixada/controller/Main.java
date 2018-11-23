package br.ufc.quixada.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.lang.Thread;

public class Main {
	static MqttClient AtoP;
	static MqttClient PtoA;
	static Thread t = new Thread();
	static Runnable ruunn = new Runnable() {
		
		void teste() throws Exception{
			String content = "MSG";
			System.out.println("Publishing message: "+content);
			MqttMessage mensagem = new MqttMessage(content.getBytes());
			mensagem.setQos(1);
			PtoA.publish("AtoP", mensagem);
			System.out.println("Enviamos algumas informações");
		}
		@Override
		public void run() {
			try {
				Random gerador = new Random();
				int x = gerador.nextInt();
				//String topic        = "PtoA";
		        String broker       = "tcp://localhost:1883";
		        String clientId     = "Servidor";
		        MemoryPersistence persistence = new MemoryPersistence();
		        MemoryPersistence persistence2 = new MemoryPersistence();
		        MqttConnectOptions connOpts = new MqttConnectOptions();
		        MqttConnectOptions connOpts2 = new MqttConnectOptions();
				connOpts.setUserName("savio_u_mqtt");
				connOpts.setPassword("mqtt_senha".toCharArray());
				connOpts.setCleanSession(true);
				connOpts2.setUserName("savio_u_mqtt");
				connOpts2.setPassword("mqtt_senha".toCharArray());
				connOpts2.setCleanSession(true);
	        	AtoP = new MqttClient(broker, clientId+"2", persistence2);
		        
	        	MqttCallback mCallback = new MqttCallback() {
	        	    @Override
	        	    public void connectionLost(Throwable cause) {
	        	      // Do nothing...
	        	    }

	        	    @Override
	        	    public void messageArrived(String topic, MqttMessage message) throws Exception {
	        	    	String payload = new String(message.getPayload());
						System.out.println("Recebi: "+payload);
						if(payload.equals("teste")){
							Publisher p = new Publisher("AtoP", "Gostei");
							p.run();
						}
	        	    }

					@Override
					public void deliveryComplete(IMqttDeliveryToken arg0) {
						// TODO Auto-generated method stub
					}

	        	  };

	        	  MqttClient PtoA = new MqttClient(broker, clientId, persistence);
	              System.out.println("Connecting to broker: "+broker);
	              PtoA.connect(connOpts);
	              PtoA.subscribe("PtoA", 1);
	              PtoA.setCallback(mCallback);
	        	  System.out.println("Deu certo?!");
	        	  
	        	  //AtoP.connect(connOpts2);
				  //System.out.println("Connected");
	        	  

				//while(true){
					/*//TimeUnit.SECONDS.sleep(5);
					//System.out.println("Aeeew "+x);					
		            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
		            MqttConnectOptions connOpts = new MqttConnectOptions();
		            connOpts.setUserName("savio_u_mqtt");
		            connOpts.setPassword("mqtt_senha".toCharArray());
		            connOpts.setCleanSession(true);
		            //System.out.println("Connecting to broker: "+broker);
		            sampleClient.connect(connOpts);
		            System.out.println("Connected");
		            System.out.println("Publishing message: "+content);
		            MqttMessage message = new MqttMessage(content.getBytes());
		            message.setQos(qos);
		            sampleClient.publish(topic, message);
		            System.out.println("Message published");
		            sampleClient.disconnect();
		            System.out.println("Disconnected");*/
		            //System.exit(0);
			 	//}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public static void main(String[] args) {
		//ThreadGroup threadGroup = new ThreadGroup("Meu grupo de threads!");
		if(!t.isAlive()){
			t = new Thread(ruunn);
			t.start();
			System.out.println("Iniciou nova Thread");
		}
	}

}
