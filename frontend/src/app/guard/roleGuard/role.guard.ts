import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { TokenService } from '../../service/tokenService/token.service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(private tokenService: TokenService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data['role']; // Role from route data
    const userRole = this.tokenService.getUserRole();

    if (userRole === expectedRole) {
      return true;
    } else {
      this.router.navigate(['/unauthorized']); // Redirect to an error page
      return false;
    }
  }
}
