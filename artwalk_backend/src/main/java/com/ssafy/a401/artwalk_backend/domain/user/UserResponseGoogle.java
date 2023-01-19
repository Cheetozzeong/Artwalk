package com.ssafy.a401.artwalk_backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserResponseGoogle {
	public String id;
	public String email;
	public Boolean verifiedEmail;
	public String name;
	public String givenName;
	public String familyName;
	public String picture;
	public String locale;
}
