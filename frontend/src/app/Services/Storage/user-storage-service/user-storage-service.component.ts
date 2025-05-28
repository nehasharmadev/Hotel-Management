import { Token } from '@angular/compiler';
import { Component } from '@angular/core';

const TOKEN = "token";
const USER = "user";
@Component({
  selector: 'app-user-storage-service',
  imports: [],
  templateUrl: './user-storage-service.component.html',
  styleUrl: './user-storage-service.component.scss'
})
export class UserStorageServiceComponent {

  constructor(){}
  static saveToken(token:string):void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN, token);
  }
  static saveUser(user:any):void{
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER, JSON.stringify(user));
  }

  static getToken():string | null{
    return localStorage.getItem(TOKEN);
  }

  static getUser(): any {
  const user = localStorage.getItem(USER);
  return user ? JSON.parse(user) : null;
}

static getUserId():any{
  const user = this.getUser();
  return user ? user.id : null;
}
static getUserRole():any{
  const user = this.getUser();
  return user ? user.role : null;
}

static isAdminLoggedIn():boolean{
  if(this.getToken == null) return false;
  const role: string = this.getUserRole();
  return role == "ADMIN";
}
static isCustomerLoggedIn():boolean{
  if(this.getToken == null) return false;
  const role: string = this.getUserRole();
  return role == "CUSTMOR";
}

static signout():void{
  window.localStorage.removeItem(TOKEN);
  window.localStorage.removeItem(USER);
}
}
