package se.klartext.app.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by suchuan on 2017-05-28.
 */
@Configuration
public class ElasticsearchConfig {

    @Value("${es.cluster_name}")
    private String clusterName;

    @Value("${es.host}")
    private String host;

    @Bean
    public TransportClient getClient() throws UnknownHostException {

        System.out.println("---------------------\n\n");
        System.out.println(clusterName);
        System.out.println(host);
        System.out.println("---------------------\n\n");

        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", false)
                .build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));

        return client;
    }
}
