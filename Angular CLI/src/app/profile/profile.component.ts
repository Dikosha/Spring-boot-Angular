import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  fullname;
  email;
  phone;


  constructor(private tokenStorage: TokenStorageService, private http: HttpClient) { }

  ngOnInit(): void {
    console.log(this.tokenStorage.getUser());

    let url = 'http://localhost:8080/user/profile';
    this.http.post <any>(url,  this.tokenStorage.getUser().id, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    })
        .subscribe(
            data => {
              console.log(data);
              /*this.user = data.RESULT;
              for (let i = 0; i < this.user.length; i++) {
                this.user[i].num = i + 1;
              }
              console.log(this.user);*/
            }
        );
  }

}
