import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css', '../../assets/css/styles.css']
})
export class RegistrationComponent implements OnInit {

  constructor(private http: HttpClient) {

  }
  model: RegistrationViewModel = {
    name: '',
    email: '',
    phone: '',
    password: '',
    re_password: ''
  };
  emailBusy = false;
  isRegistered = false;

  ngOnInit(): void {
  }

  registration(): void {
    const url = 'http://localhost:8080/api/registration';
    this.http.post(url, this.model).subscribe(
      res => {
        console.log(res);
        // @ts-ignore
        if (res.STATUS === 200) {
            location.href = 'login';
        } else {
            alert('Not registered. Try again.');
        }
      },
      error => {
        alert('Something is wrong');
      }
    );
  }
  checkEmail(email): void {
    this.emailBusy = false;
    // console.log(email);
    if (email != '') {
      const url = 'http://localhost:8080/api/checkEmail';
      this.http.post(url, email)
        .subscribe(
          res => {
            // console.log(res);
            // @ts-ignore
            if (res.STATUS === 302) {
              this.emailBusy = true;
            } else {
              this.emailBusy = false;
            }
            // location.reload();
          },
          error => {
            alert('An error occured');
          }
        );
    }
  }

}

export interface RegistrationViewModel {
  name: string;
  email: string;
  phone: string;
  password: string;
  re_password: string;
}
