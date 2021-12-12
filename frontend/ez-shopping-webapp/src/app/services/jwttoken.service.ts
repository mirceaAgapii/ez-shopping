import { Injectable } from '@angular/core';
import jwt_decode from "jwt-decode";
import { LocalStorageService } from './local-storage.service';

@Injectable()
export class JWTTokenService {

    jwtToken!: string;
    decodedToken!: { [key: string]: string; };

    constructor(private localStorageService: LocalStorageService) {
    }

    setToken(token: string) {
      if (token) {
        this.jwtToken = token;
      }
    }

    decodeToken() {
      if (!this.jwtToken) {
        const token = this.localStorageService.get('access_token');
        if(token !== null) {
          this.jwtToken = token;
        } else {
          this.jwtToken = '';
        }
      }
      this.decodedToken = jwt_decode(this.jwtToken);
    }

    getDecodeToken() {
      return jwt_decode(this.jwtToken);
    }

    getUser() {
      this.decodeToken();
      return this.decodedToken ? this.decodedToken.sub : null;
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
}