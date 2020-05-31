import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ActivatedRoute, Router, Routes} from '@angular/router';
import {TokenStorageService} from '../_services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../../assets/css/styles.css']
})
export class LoginComponent implements OnInit {
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient,
              private tokenStorage: TokenStorageService) {
  }

  model: LoginViewModel = {
    email: '',
    password: ''
  };

  ngOnInit(): void {
      console.log(this.tokenStorage.getUser());

      if (this.tokenStorage.getUser().id) {
          console.log(123);
          window.location.href = 'reserve';
      }
  }

  login(): void {
    const url = 'http://localhost:8080/main/signin';
    this.http.post <any>(url, {
      email: this.model.email,
      password: this.model.password
    }, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    })
      .subscribe(
          data => {
            this.tokenStorage.saveToken(data.accessToken);
            this.tokenStorage.saveUser(data);
            this.isLoginFailed = false;
            this.isLoggedIn = true;
            this.roles = this.tokenStorage.getUser().roles;
            window.location.href = '';
          }
      );
  }
}

export interface LoginViewModel {
  email: string;
  password: string;
}
