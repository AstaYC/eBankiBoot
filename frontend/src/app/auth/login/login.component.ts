import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../authService/auth.service';
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
    private router: Router
  ) {
    // Initialize the login form with validation rules
    this.loginForm = this.fb.group({
      email: ['john.doe2@example.com'], // No validators
      password: ['password123'], // No validators
    });
  }

  // Method to handle form submission
  onSubmit() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: (response) => {
          console.log('Login successful', response);
          this.router.navigate(['dashboard']);
        },
        error: (error) => {
          console.error('Login failed', error);
          alert('Login failed: ' + error.error.message);
        },
      });
    }
  }
}
