import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly key = 'jwt';

  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    return this.http.post<{ token: string }>(`${environment.apiUrl}/auth/login`, { username, password })
      .pipe(tap(res => {
        if (typeof localStorage !== 'undefined') {
          localStorage.setItem(this.key, res.token);
        }
      }));
  }

  register(username: string, password: string) {
    return this.http.post<{ token: string }>(`${environment.apiUrl}/auth/register`, { username, password })
      .pipe(tap(res => {
        if (typeof localStorage !== 'undefined') {
          localStorage.setItem(this.key, res.token);
        }
      }));
  }

  get token(): string | null {
    if (typeof localStorage === 'undefined') {
      return null;
    }
    return localStorage.getItem(this.key);
  }

  isLoggedIn(): boolean {
    return !!this.token;
  }

  logout(): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem(this.key);
    }
  }
}
