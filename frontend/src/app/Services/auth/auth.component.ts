import { HttpClient } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASIC_URL = "http://localhost:8080"
@Injectable({
  providedIn:'root'
})
@Component({
  selector: 'app-auth',
  imports: [],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent {
constructor(private http: HttpClient){}
register(signupRequest:any) : Observable<any>{
return this.http.post(BASIC_URL + "/api/auth/signup", signupRequest);
}

login(loginReq : any) : Observable<any>{
  return this.http.post(BASIC_URL+"/api/auth/login", loginReq);
}
}
