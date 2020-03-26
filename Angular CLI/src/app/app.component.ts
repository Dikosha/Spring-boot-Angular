import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from './_services/token-storage.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ScriptLoadingService} from './_services/script-loading.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  roles: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  username: string;
  title = 'my-app';

  constructor(private tokenStorageService: TokenStorageService, private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void  {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.username = user.username;
    }
  }

}
