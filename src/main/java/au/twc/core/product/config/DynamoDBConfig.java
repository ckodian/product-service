package au.twc.core.product.config;

import au.twc.core.product.domain.AbstractEntity;
import au.twc.core.product.dynamo.utils.InstantProvider;
import au.twc.core.product.security.SecurityUtils;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.socialsignin.spring.data.dynamodb.config.EnableDynamoDBAuditing;
import org.socialsignin.spring.data.dynamodb.mapping.DynamoDBMappingContext;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;

@Configuration
@EnableDynamoDBRepositories(basePackages = "au.twc.core.product.repository")
@EnableDynamoDBAuditing
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.dynamodb.region}")
    private String amazonDynamoDBRegion;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration
            = new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDBRegion);

        AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(endpointConfiguration)
            .withCredentials(new AWSCredentialsProvider() {
                @Override
                public AWSCredentials getCredentials() {
                    return amazonAWSCredentials();
                }

                @Override
                public void refresh() {

                }
            })
            .build();

        return dynamoDB;
    }

    @Bean
    @Primary
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

    @Bean
    @Primary
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB());
    }

    @Bean
    public AuditorAware<String> userAuditing() {
        return () -> SecurityUtils.getCurrentUserLogin();
    }

    @Bean
    public DateTimeProvider dateAuditing() {
        return InstantProvider.INSTANCE;
    }

    @Bean
    public DynamoDBMappingContext dynamoDBMappingContext() {
        DynamoDBMappingContext mappingContext = new DynamoDBMappingContext();
        mappingContext.getPersistentEntity(AbstractEntity.class);
        return mappingContext;
    }
}
