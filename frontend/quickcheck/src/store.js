import { createStore, action, thunk } from 'easy-peasy';

import { api } from './utils/apiClient';

// Documentation for easy-peasy: https://easy-peasy.vercel.app/docs/tutorials/quick-start.html

// first define the store model, then add actions to change the stored state and add 'thunks' for actions with side effects (e.g. api calls)


const productAreaModel = {
  products: [
    /*{
      productID: 0,
      productName: 'asdfasdf',
      productArea: {
        "id": "1",
        "name": "Kredit",
      "category": "Privat"},
      projectID: 1,
      parentID: 0,
    },*/
    {
      productID: 112,
      productName: 'Optionen 2 State  ',
      productArea: {},
      projectID: 1,
      parentID: 0,
    },
    {
      productID: 113,
      productName: 'Optionen child111',
      productArea: {},
      projectID: 1,
      parentID: 111,
    },
  ],

  addProduct: action((state) => {
    //state.products = {...state.products, product}
    console.log("Its working")
  }),
  removeProduct: action((state, product) => {
    state.products = state.products.filter((p) => p.productName !== product.productName);
  })

  /*updateProject: action((state, payload) => {
    state.project = { ...state.project, ...payload }; // to change name pass {name: 'new name' } as payload
  }),

  fetchProject: thunk(async (actions, payload) => {
    await api
      .url('/projects/' + payload.projectID)
      .get()
      .json((json) => actions.updateProject(json))
      .catch(console.error);
  }),*/
}

const projectListModel = " ";
const projectModel = " ";

const store = createStore({
  projectList: projectListModel,
  project: projectModel,
  productList: productAreaModel
});

export default store;
