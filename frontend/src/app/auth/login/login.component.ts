import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import {ActivatedRouteSnapshot, CanActivate, Router} from '@angular/router';
import { AuthService } from '../../service/authService/auth.service';
import { TokenService } from '../../service/tokenService/token.service';
import { CommonModule } from '@angular/common';



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule], // Add ReactiveFormsModule here
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;


  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private tokenService: TokenService,
  ) {
    // Initialize the login form with validation rules
    this.loginForm = this.fb.group({
      email: ['', [Validators.required,]], // Validators added
      password: ['', Validators.required], // Validators added
    });
  }

  // Method to handle form submission
  onSubmit() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: (response : string) => {
          console.log('Login successful', response);
          this.tokenService.saveToken(response);
            console.log(this.tokenService.getUserRole());

          if(this.tokenService.getUserRole() == 'ADMIN'){
            this.router.navigate(['admin']);
          }else if (this.tokenService.getUserRole() == 'USER'){
            this.router.navigate(['user']);
          }else {
            this.router.navigate(['employee']);
          }
        },
        error: (error) => {
          console.error('Login failed', error);
          alert('Login failed: ' + error.error.message);
        },
      });
    }
  }
}
