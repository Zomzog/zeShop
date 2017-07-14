import { AdminWebPage } from './app.po';

describe('admin-web App', () => {
  let page: AdminWebPage;

  beforeEach(() => {
    page = new AdminWebPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
