import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthorizationService } from '../services/auth/authorization.service';
import { JWTTokenService } from '../services/auth/jwttoken.service';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AdminPermisionsGuard implements CanActivate {

  constructor(
    private jwtService: JWTTokenService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot, 
    state: RouterStateSnapshot
  ): boolean {
    if (this.jwtService.getUserRoles()?.includes('ADMINISTRATOR')) {
      
      return true;
    }
  
    return false;
  }
}
