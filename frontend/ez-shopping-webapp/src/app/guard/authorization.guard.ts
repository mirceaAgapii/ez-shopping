import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs'; 

import { LocalStorageService } from '../services/auth/storage/local-storage.service';
import { JWTTokenService } from '../services/auth/jwttoken.service';
import { UserRestService } from '../services/rest/user/user.rest.service';
import { AuthorizationService } from '../services/auth/authorization.service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationGuard implements CanActivate {

  constructor(private authService: AuthorizationService,
    private router: Router) {
}

canActivate(
  route: ActivatedRouteSnapshot, 
  state: RouterStateSnapshot
): boolean {
  if (!this.authService.isAuthenticated()) {
    this.router.navigate(['/login']);
    return false;
  }

  return true;
}
  
}
