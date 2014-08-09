package com.ofg.judge

import com.google.common.eventbus.EventBus
import com.ofg.infrastructure.environment.EnvironmentSetupVerifier
import com.ofg.judge.event.listener.RelationshipEventListener
import com.ofg.microservice.Profiles
import groovy.transform.TypeChecked
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@TypeChecked
@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = ["com.ofg.judge", "com.mangofactory.swagger"])
@EnableCaching
class Application {

    static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application)
        application.addListeners(new EnvironmentSetupVerifier(Profiles.all()))
        application.run(args)
    }
	
	@Bean
	EventBus eventBus() {
		EventBus eventBus = new EventBus()
		
		eventBus.register(new RelationshipEventListener())
		
		return eventBus
	}
}
