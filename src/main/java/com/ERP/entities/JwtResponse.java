package com.ERP.entities;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data

public class JwtResponse {
    private String jwtToken;
    private String username;
    List<String> roles;
}
