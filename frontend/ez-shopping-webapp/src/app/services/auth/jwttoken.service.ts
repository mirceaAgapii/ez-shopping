import { Route } from '@angular/compiler/src/core';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import jwt_decode from "jwt-decode";
import { LocalStorageService } from './storage/local-storage.service';

@Injectable()
export class JWTTokenService {

    jwtToken!: string;
    decodedToken!: { [key: string]: string; };

    ACCESS_TOKEN = 'access_token';
    REFRESH_TOKEN = 'refresh_token'

    constructor(private localStorageService: LocalStorageService,
      private router: Router) {
    }

    setToken(token: string) {
      if (token) {
        this.jwtToken = token;
      } 
    }

    saveAccessToken(token: string) {
      this.localStorageService.set(this.ACCESS_TOKEN, token);
    }

    saveRefreshToken(token: string) {
      this.localStorageService.set(this.REFRESH_TOKEN, token);
    }

    getAccessToken(): string | null {
      return this.localStorageService.get('access_token');
    }

    getRefreshToken(): string | null {
      return this.localStorageService.get('refresh_token');
    }


    decodeToken() {
      
      const token = this.localStorageService.get('access_token');
      if(token) {
        this.decodedToken = jwt_decode(token);
      }
      else {
        this.localStorageService.remove('refresh_token');
        this.localStorageService.remove('access_token');
        this.localStorageService.remove('userId');
        this.router.navigate(['/login'])
      }
      
    }

    getDecodeToken() {
      return jwt_decode(this.jwtToken);
    }

    getUser() {
      this.decodeToken();
      return this.decodedToken ? this.decodedToken.sub : null;
    }

    getUserId() {
      this.decodeToken();
      return this.decodedToken ? this.decodedToken.userId : null;
    }

    getUserRoles() {
      this.decodeToken();
      return this.decodedToken ? this.decodedToken.roles : null;
    }

    getExpiryTime() {
      this.decodeToken();
      return this.decodedToken ? this.decodedToken.exp : null;
    }

    isTokenExpired(): boolean {
      const expiryTime: number = this.getExpiryTime() as unknown as number;
      if (expiryTime) {
        return ((1000 * expiryTime) - (new Date()).getTime()) < 5000;
      } else {
        return false;
      }
    }

    signOut(): void {
      window.sessionStorage.clear();
    }
}