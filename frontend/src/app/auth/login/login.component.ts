import { Component } from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import { AuthService} from "../../service/authService/auth.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {NgIf} from "@angular/common";
import {TokenService} from "../../service/tokenService/token.service";


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    HttpClientModule,
    NgIf
  ],
  templateUrl: './login.component.html',
})

export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private authService: AuthService,
              private router: Router,
              private tokenService: TokenService,

  ) { }

  login() {
    if(!this.email || !this.password){
      this.errorMessage = 'All fields are required';
      this.successMessage = '';
      return;
    }

    this.authService.login(this.email, this.password).subscribe({
      next: (response) => {
        if(response){
          this.successMessage = 'Login successful';
          this.errorMessage = '';

          this.tokenService.saveToken(response);

          if(this.tokenService.getUserRole() == 'ADMIN'){
            this.router.navigate(['/admin/dashboard']);
          }else if (this.tokenService.getUserRole() === 'EMPLOYEE'){
            this.router.navigate(['/employee/dashboard']);
          } else{
            this.router.navigate(['/user/dashboard']);
          }
        } else {
          this.errorMessage = 'Login failed. Please check your credentials';
          this.successMessage = '';
        }
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'login failed. Pleas check your credentials';
        this.successMessage = '';
        console.error('Login failed',error);

      }
    });
  }
}
