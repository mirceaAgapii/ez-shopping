import { Injectable } from '@angular/core';

@Injectable()
export class LocalStorageService {

    set(key: string, value: string) {
        localStorage.setItem(key, value);
    }

    get(key: string): string {
        const result = localStorage.getItem(key);
        if (result) {
            return result;
        }
        return "";
    }

    remove(key: string) {
        localStorage.removeItem(key);
    }
}