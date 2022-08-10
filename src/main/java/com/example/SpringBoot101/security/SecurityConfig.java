package com.example.SpringBoot101.security;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // Ajouter a la classe qui doit etre traiter au démarrage de l'application
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Methode qui permet de configurér les users.
        // Avec auth on peut specifie la stratégie de stockage des users
        // inMemoryAuthentication est la stratégie le plus simple pour le stockage en memoire
        auth.inMemoryAuthentication().withUser("ie017").password("{noop}1234").roles("USER");
        auth.inMemoryAuthentication().withUser("ie018").password("{noop}1234").roles("USER");
        auth.inMemoryAuthentication().withUser("ie019").password("{noop}1234").roles("USER","ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Si le corps de cette methode est vide alors le systeme d'authentification est desactivée
        http.formLogin();// Pour avoir un formulaire d'authentification.
        //Vous ne devez pas appeler super.configure(http) car vous souhaitez utiliser une configuration de sécurité personnalisée.
        //L'erreur est due au fait que la méthode parent configure(http) appelle déjà .authorizeRequests().anyRequest().authenticated()
        // et comme le mentionne le message d'erreur, Generalement la configuration de sécurité personnalisée doit etre declarée premierement.
        http.authorizeRequests().antMatchers("/add**/**","delete**/**").hasRole("ADMIN");
        //http.httpBasic(); formulaire de login js
        http.authorizeRequests().anyRequest().authenticated(); // C'est a dire on configurer notre spring security
        // pour doit etre un authentification pour toutes les requetes mais SS ne sait comment cette operation
        // effectue solution use formatLogin

        /*Spring boot activée csrf detection par defaut
        * et on peut regarder ça dans les furmulaier de notre app avec l'utilisation de l'element inspect dans notre browser:
        * <input type="hidden" name="_csrf" value="12df74a2-255b-46e5-9c59-fd56192f108e"> ça permet de verifie que user generée leur request a partir
        * leur navigateur */

        /* Pour le desactivé */
        http.csrf().disable();
    }
}
