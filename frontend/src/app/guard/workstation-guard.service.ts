import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthorizationService} from "../services/auth/authorization.service";
import {JWTTokenService} from "../services/auth/jwttoken.service";

@Injectable({
  providedIn: 'root'
})
export class WorkstationGuard implements CanActivate {
  constructor(private authorizationService: AuthorizationService,
              private jwtService: JWTTokenService,
              private router: Router) {
  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    if (this.authorizationService.isUserWorkstation()) {
      let userRoles = this.jwtService.getUserRoles();
      if (userRoles !== null) {
        if (userRoles.includes('CHECKOUT')) {
          this.router.navigate(['/station']);
        } else if (userRoles.includes('RECEIVING')) {
          this.router.navigate(['/receiving']);
        }
      }
      return false;
    }
    return true;
  }

}
