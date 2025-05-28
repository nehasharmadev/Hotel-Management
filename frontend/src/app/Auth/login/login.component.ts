import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { AuthComponent } from '../../Services/auth/auth.component';
import { UserStorageServiceComponent } from '../../Services/Storage/user-storage-service/user-storage-service.component';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NzFormModule,
    NzInputModule,
    NzButtonModule
  ]
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  constructor(private fb: FormBuilder, private authService : AuthComponent, private router: Router) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required]
    });
  }

  onSubmit(): void {
    this.authService.login(this.loginForm.value).subscribe((res: any) => {
       console.log('Login successful:', res);
       if(res.userId != null){
        const user = {
          id: res.userId,
          role: res.userRole
        }
        UserStorageServiceComponent.saveUser(user);
        UserStorageServiceComponent.saveToken(res.jwt);
       }
       if(UserStorageServiceComponent.isAdminLoggedIn())
           this.router.navigateByUrl("/admin/dashboard");
       else if (UserStorageServiceComponent.isCustomerLoggedIn()){
           this.router.navigateByUrl("/customer/rooms")
       }
       
});
  }
}
