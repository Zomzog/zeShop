import { AdminFrontPage } from './app.po';

describe('admin-front App', () => {
  let page: AdminFrontPage;

  beforeEach(() => {
    page = new AdminFrontPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
