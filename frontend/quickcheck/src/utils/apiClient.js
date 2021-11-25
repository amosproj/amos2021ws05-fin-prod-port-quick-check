import wretch from 'wretch';
// For documentation see: https://github.com/elbywan/wretch

const backendURL = 'http://localhost:8080';

const networkErrorHandler = (err) => {
  console.error('Network error during fetching occurred:', err);
};

const wretcherErrorHandler = (err) => {
  console.error(
    'Caught error on API request. Statuscode:',
    err.response.status,
    'Message:',
    err.message
  );
};

export const api = wretch()
  .url(backendURL)
  // .catcher(404, (err) => alert('default handler for 404'))  // EXAMPLE
  // Default error handler
  .catcher('Error', (err) => wretcherErrorHandler(err))
  // Default error handler for network errors that occurred during fetching
  .catcher('__fromFetch', (err) => networkErrorHandler(err));
