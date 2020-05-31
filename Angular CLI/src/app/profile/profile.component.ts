import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  userName;
  userEmail;
  userPhone;
  userRecords = [];
  userId = this.tokenStorage.getUser().id;


  constructor(private tokenStorage: TokenStorageService, private http: HttpClient) { }

    model: UserInfViewModel = {
        id: this.userId,
        name: '',
        phone: '',
        email: ''
    };

  ngOnInit(): void {
    console.log(this.tokenStorage.getUser());
    const url = 'http://localhost:8080/user/profile';
    this.http.post <any>(url,  this.userId, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    })
        .subscribe(
            data => {
              console.log(data);
              this.userName = data.RESULT.user.user_name;
              this.userPhone = data.RESULT.user.user_phone;
              this.userEmail = data.RESULT.user.user_email;
              this.userRecords = data.RESULT.records;
            }
        );
  }

    editProfile(): void {
        // @ts-ignore
        this.model.name = document.getElementById('name').value;
        this.model.phone = (document.getElementById('phone') as HTMLInputElement).value;
        this.model.email = (document.getElementById('email') as HTMLInputElement).value;
        console.log(this.model);
        this.http.post('http://localhost:8080/user/editInformation', this.model).subscribe(
            res => {
                console.log(res);
                // @ts-ignore
                if (res.STATUS === 200) {
                    location.reload();
                } else {
                    alert('Not succeed. Try again.');
                }
            },
            error => {
                alert('Something is wrong');
            }
        );
    }

    updatePassword(): void {
        // @ts-ignore
        // tslint:disable-next-line:prefer-const
        let oldPass = document.getElementById('old').value;
        const newPass = (document.getElementById('new') as HTMLInputElement).value;
        this.http.post('http://localhost:8080/user/updatePassword',
            {id: this.userId, oldPassword: oldPass, newPassword: newPass }).subscribe(
            res => {
                console.log(res);
                // @ts-ignore
                alert(res.RESULT + '.  ' + res.ERROR);
                location.reload();
            },
            error => {
                alert('Something is wrong');
            }
        );
    }

}

export interface UserInfViewModel {
    id: number;
    name: string;
    phone: string;
    email: string;
}

