import { Routes } from '@angular/router';
import {ChatComponent} from './components/chat/chat.component';
import {CryptosComponent} from './components/cryptos/cryptos.component';
import {ViewComponent} from './components/view/view.component';
import {LandingPageComponent} from './components/landing-page/landing-page.component';

export const routes: Routes = [
  {path: "", component: LandingPageComponent},
  {path: "view", component: ViewComponent, children:[
      {path: "ai-chatbot", component: ChatComponent},
      {path: "cryptos", component: CryptosComponent}
    ]},
];
