package com.cortezdevdiego.talent_guard.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // --- LOGS DE DEBUG PARA DESCOBRIR O ERRO 403 ---
        System.out.println("\n------------------------------------------------");
        System.out.println(">>> 1. REQUEST RECEBIDO: " + request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");
        System.out.println(">>> 2. HEADER AUTHORIZATION: " + authHeader);

        final String jwt;
        final String userEmail;

        // Verifica se o cabeçalho existe e começa com "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println(">>> 🚫 BLOQUEADO: Header vazio ou formato incorreto (sem 'Bearer ')");
            filterChain.doFilter(request, response);
            return;
        }

        // Extrai o token (pula os 7 caracteres de "Bearer ")
        jwt = authHeader.substring(7);
        System.out.println(">>> 3. TOKEN EXTRAÍDO: " + jwt);

        try {
            userEmail = jwtService.extractUsername(jwt);
            System.out.println(">>> 4. EMAIL DENTRO DO TOKEN: " + userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Autoriza o usuário
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println(">>> ✅ SUCESSO: Usuário autenticado e liberado!");
                } else {
                    System.out.println(">>> ❌ ERRO: O token é inválido para este usuário!");
                }
            }
        } catch (Exception e) {
            System.out.println(">>> ☠️ ERRO CRÍTICO AO LER TOKEN: " + e.getMessage());
            e.printStackTrace(); // Mostra o erro detalhado no terminal
        }

        System.out.println("------------------------------------------------\n");
        filterChain.doFilter(request, response);
    }
}