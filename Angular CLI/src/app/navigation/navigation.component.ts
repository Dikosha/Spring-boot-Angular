import { Component, OnInit } from '@angular/core';
import {AppComponent} from '../app.component';
import {TokenStorageService} from '../_services/token-storage.service';
import {ScriptLoadingService} from '../_services/script-loading.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  pageUrl = '';
  constructor(public appComponent: AppComponent, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.appComponent.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.appComponent.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.appComponent.roles = user.roles;

      this.appComponent.showAdminBoard = this.appComponent.roles.includes('ROLE_ADMIN');
      this.appComponent.username = user.username;
    }
  }


  logout() {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

}
