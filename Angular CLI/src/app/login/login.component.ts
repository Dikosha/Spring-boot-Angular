import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ActivatedRoute, Router, Routes} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient) {
  }

  model: LoginViewModel = {
    email: '',
    password: ''
  };

  ngOnInit(): void {
  }

  login(): void {
    const url = 'http://localhost:8080/login';
    this.http.post<Observable<boolean>>(url,{
      userName: this.model.email,
      password: this.model.password
    })
      .subscribe(isValid => {
        if (isValid) {
          sessionStorage.setItem(
            'token',
            btoa(this.model.email + ':' + this.model.password)
          );
          this.router.navigate(['']);
        } else {
          alert('Authentication failed.');
        }
      });
  }
}

export interface LoginViewModel {
  email: string;
  password: string;
}
