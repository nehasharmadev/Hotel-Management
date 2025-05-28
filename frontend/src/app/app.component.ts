import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Router, NavigationEnd, RouterModule } from '@angular/router';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzSliderModule } from 'ng-zorro-antd/slider';
import { RouterLink } from '@angular/router';
import { UserStorageServiceComponent } from './Services/Storage/user-storage-service/user-storage-service.component';
import { filter } from 'rxjs/operators';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [NzButtonModule, NzLayoutModule,  NzSliderModule, RouterOutlet, RouterLink,CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
  isCustomerLoggedIn : boolean = UserStorageServiceComponent.isCustomerLoggedIn();
  isAdminLoggedIn : boolean = UserStorageServiceComponent.isAdminLoggedIn();
  constructor(private router: Router){}
 ngOnInit() {
  this.router.events
    .pipe(filter(event => event instanceof NavigationEnd))
    .subscribe((event) => {
      console.log('Route changed to:', (event as NavigationEnd).urlAfterRedirects);
      // Update login status or UI here
      this.isCustomerLoggedIn = UserStorageServiceComponent.isCustomerLoggedIn();
      this.isAdminLoggedIn = UserStorageServiceComponent.isAdminLoggedIn();
    });
}

logout(){
  UserStorageServiceComponent.signout();
  this.router.navigateByUrl("/");
}

}

