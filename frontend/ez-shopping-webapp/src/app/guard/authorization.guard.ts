import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs'; 

import { LocalStorageService } from '../services/local-storage.service';
import { JWTTokenService } from '../services/jwttoken.service';
import { UserRestService } from '../services/user.rest.service';
import { AuthorizationService } from '../services/auth/authorization.service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationGuard implements CanActivate {

  constructor(private authService: AuthorizationService,
    private localStorageService: LocalStorageService,
    private jwtService: JWTTokenService,
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
