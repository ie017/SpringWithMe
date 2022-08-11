package com.example.SpringBoot101.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration // Ajouter a la classe qui doit etre traiter au démarrage de l'application
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Methode qui permet de configurér les users.
        PasswordEncoder passwordEncoder = passwordEncoder();
        System.out.println("*************************************");
        System.out.println(passwordEncoder.encode("1234")); // Pour afichée l'encodage de 1234 qui varie
        // a chaque fois qaund on executée le programe (et ça c'est le point fort de BCrypte Algo)
        System.out.println("*************************************");
        // Avec auth on peut specifie la stratégie de stockage des users
        // inMemoryAuthentication est la stratégie le plus simple pour le stockage en memoire
        auth.inMemoryAuthentication().withUser("ie017").password(passwordEncoder.encode("1234")).roles("USER");
        auth.inMemoryAuthentication().withUser("ie018").password("{noop}1234").roles("USER"); // noop pour eviter l'encodage de 1234
        auth.inMemoryAuthentication().withUser("ie019").password(passwordEncoder.encode("1234")).roles("USER","ADMIN");
        // Avec JDBC authentification
        auth.jdbcAuthentication().dataSource(dataSource).// usersByUsernameQuery() permit de specifie la requet sql
                usersByUsernameQuery("Select username as principal, password as credentials, active From users where username=?")
        // ? c'est a dire si username egale le username inserée dans le formulaire
        // as principal pour spring boot identifie username comme username
        // as credentials pour spring boot identifie password element dans la table comme password
        .authoritiesByUsernameQuery("Select username as principal, role as role from users_roles where username=?")
        // as role pour spring boot identifie un element dans la table comme role
                .passwordEncoder(passwordEncoder) // Pour l'encoder a BCrypte remarque dans la base de données on a utilisée MD5 mais je le remplacer par BCrypte
                .rolePrefix("ROLE_");// pour ajouter un prefix a role like ROLE_ADMIN.
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Si le corps de cette methode est vide alors le systeme d'authentification est desactivée
        //http.formLogin();// Pour avoir un formulaire d'authentification par defaut de spring.
        http.formLogin().loginPage("/login");
        //Vous ne devez pas appeler super.configure(http) car vous souhaitez utiliser une configuration de sécurité personnalisée.
        //L'erreur est due au fait que la méthode parent configure(http) appelle déjà .authorizeRequests().anyRequest().authenticated()
        // et comme le mentionne le message d'erreur, Generalement la configuration de sécurité personnalisée doit etre declarée premierement.
        http.authorizeRequests().antMatchers("/add**/**","/Delete**/**","/edit**/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/city").hasRole("USER");
        http.authorizeRequests().antMatchers("/login", "/webjars/**").permitAll();
        //http.httpBasic(); formulaire de login js
        http.authorizeRequests().anyRequest().authenticated(); // C'est a dire on configurer notre spring security
        // pour doit etre un authentification pour toutes les requetes mais SS ne sait comment cette operation
        // effectue solution use formatLogin

        /*Spring boot activée csrf detection par defaut
        * et on peut regarder ça dans les furmulaier de notre app avec l'utilisation de l'element inspect dans notre browser:
        * <input type="hidden" name="_csrf" value="12df74a2-255b-46e5-9c59-fd56192f108e"> ça permet de verifie que user generée leur request a partir
        * leur navigateur */

        /* Pour le desactivé */
        // http.csrf().disable();

        http.exceptionHandling().accessDeniedPage("/notAutorized");
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
