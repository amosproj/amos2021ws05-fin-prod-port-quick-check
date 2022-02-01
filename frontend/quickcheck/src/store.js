import { createStore, action, thunk, computed } from 'easy-peasy';

import { api } from './utils/apiClient';
import { ratingArea, score } from './utils/const';

// Documentation for easy-peasy: https://easy-peasy.vercel.app/docs/tutorials/quick-start.html

// first define the store model, then add actions to change the stored state and add 'thunks' for actions with side effects (e.g. api calls)

const productAreaModel = {
  items: [
    /*{
      productID: 0,
      productName: '',
      productArea: {
        "id": "1",
        "name": "Kredit",
      "category": "Privat"},
      projectID: 1,
      parentID: 0,
    }*/
  ],

  products: computed((state) => state.items.filter((p) => p.parentID === 0)),

  getVariants: computed((state) => {
    return (product) => state.items.filter((p) => p.parentID === product.productID);
  }),

  getAreaProducts: computed((state) => {
    return (areaID) => state.products.filter((p) => p.productArea.id === areaID);
  }),

  set: action((state, products) => {
    state.items = products;
  }),
  addProduct: action((state, product) => {
    state.items.push(product);
  }),

  updateProductName: action((state, { productID, newName }) => {
    const index = state.items.map((p) => p.productID).indexOf(productID);
    state.items[index] = { ...state.items[index], productName: newName };
  }),
  updateProductComment: action((state, { productID, newComment }) => {
    const index = state.items.map((p) => p.productID).indexOf(productID);
    state.items[index] = { ...state.items[index], comment: newComment };
  }),
  removeProduct: action((state, product) => {
    state.items = state.items.filter((p) => p.productID !== product.productID);
  }),
  fetch: thunk(async (actions, id) => {
    console.log('/projects/' + id + '/products');
    await api
      .url('/projects/' + id + '/products')
      .get()
      .json((json) => actions.set(json))
      .catch(console.error);
  }),
  createProduct: thunk(async (actions, newProduct) => {
    console.log(JSON.stringify(newProduct));
    await api
      .url('/projects/' + newProduct.projectID + '/products')
      .post(newProduct)
      .res()
      .catch(console.error);
    actions.fetch(newProduct.projectID);
  }),
  updateAllProducts: thunk(async (actions, products) => {
    products.map((product) => actions.updateProduct(product));
  }),

  updateProduct: thunk(async (actions, product) => {
    await api
      .url('/products/' + product.productID)
      .put(product)
      .res()
      .catch(console.error);
  }),
};

const resultModel = {
  results: [],
  set: action((state, results) => {
    state.results = results;
  }),
  fetch: thunk(async (actions, id) => {
    console.log('/projects/' + id + '/results');
    await api
      .url('/projects/' + id + '/results')
      .get()
      .json((json) => actions.set(json))
      .catch(console.error);
  }),
};

const projectListModel = {
  items: [], // list of: {"projectID": 2,"projectName": "Mock Project" }

  set: action((state, items) => {
    state.items = items;
  }),
  add: action((state, newProject) => {
    state.items.push(newProject);
  }),

  fetch: thunk(async (actions, payload) => {
    await api
      .url('/projects')
      .get()
      .json((json) => actions.set(json))
      .catch(console.error);
  }),
};

const projectModel = {
  data: {
    projectID: 0,
    projectName: '',
    creatorID: '0fef539d-69be-4013-9380-6a12c3534c67',
    members: [],
    productAreas: [],
  },

  init: action((state, payload) => {
    state.data = {
      projectID: 0,
      creatorID: '0fef539d-69be-4013-9380-6a12c3534c67',
      projectName: '',
      members: [],
      productAreas: [],
    };
  }),
  // general actions
  set: action((state, project) => {
    state.data = project;
  }),
  update: action((state, updatedProps) => {
    state.data = { ...state.data, ...updatedProps };
  }),

  setProjectName: action((state, projectName) => {
    state.data.projectName = projectName;
  }),
  addMember: action((state, newMember) => {
    state.data.members.push(newMember);
  }),
  removeMember: action((state, member) => {
    // remove member with matching email from items
    state.data.members = state.data.members.filter((m) => m.userEmail !== member.userEmail);
  }),
  updateMember: action((state, member) => {
    // overwrite member with same email
    const index = state.data.members.map((m) => m.userEmail).indexOf(member.userEmail); // get index of member with same email. if not found, index=-1
    state.data.members[index] = { ...state.data.members[index], ...member };
  }),
  addProductArea: action((state, newArea) => {
    state.data.productAreas.push(newArea);
  }),
  removeProductArea: action((state, remArea) => {
    // remove member with matching email from items
    state.data.productAreas = state.data.productAreas.filter((a) => a.id !== remArea.id);
  }),

  // GET project by id
  fetch: thunk(async (actions, id) => {
    await api
      .url('/projects/' + id)
      .get()
      .json((json) => actions.set(json))
      // .json((json) => console.log(json))
      .catch(console.error);
  }),

  // POST new Project
  sendCreate: thunk(async (actions, projectData) => {
    console.log('send CREATE project:', { projectData });
    await api
      .url('/projects')
      .post(projectData)
      .json((json) => actions.set(json))
      .catch(console.error);
  }),

  sendUpdate: thunk(async (actions, projectData) => {
    console.log('send UPDATE project:', { projectData });
    actions.set(projectData);
    await api
      .url(`/projects/` + String(projectData.projectID))
      .put(projectData)
      .res(console.log)
      .catch(console.error);

    actions.set(projectData);
  }),
};

const product_rating = {
  product: {
    productID: 0,
    ratingID: 0,
    ratings: [
      {
        ratingID: 10,
        answer: 'test answer',
        comment: 'test comment',
        score: score.gering,
        rating: {
          category: 'Treiber 1',
          criterion: 'test frage',
          ratingArea: ratingArea.ECONOMIC,
        },
      },
    ],
  },
  changeAnswer: action((state, rat) => {
    let index = state.product.ratings.map((r) => r.ratingID).indexOf(rat.ratingID);
    state.product.ratings[index] = { ...state.product.ratings[index], answer: rat.answer };
  }),

  changeComment: action((state, rat) => {
    let index = state.product.ratings.map((r) => r.ratingID).indexOf(rat.ratingID);
    state.product.ratings[index] = { ...state.product.ratings[index], comment: rat.comment };
  }),
};

const ratingModel = {
  product: {
    productID: -1,
    ratingID: 0,
    ratings: [
      {
        ratingID: 10,
        answer: 'test answer',
        comment: 'test comment',
        score: score.gering,
        rating: {
          category: 'Treiber 1',
          criterion: 'test frage',
          ratingArea: ratingArea.ECONOMIC,
        },
      },
    ],
  },

  init: action((state, payload) => {
    state.product = [
      {
        productID: -1,
        ratingID: 0,
        answer: 'test answer',
        comment: 'test comment',
        score: score.gering,
        rating: {
          ratingID: 0,
          category: 'Treiber 1',
          criterion: 'test frage',
          ratingArea: ratingArea.ECONOMIC,
        },
      },
    ];
  }),

  // general actions
  set: action((state, ratings) => {
    state.product = ratings;
  }),
  update: action((state, updatedProps) => {
    state.product = { ...state.product, ...updatedProps };
  }),

  changeAnswer: action((state, rat) => {
    let index = state.product.ratings.map((r) => r.ratingID).indexOf(rat.ratingID);
    state.product.ratings[index] = { ...state.product.ratings[index], answer: rat.answer };
  }),

  // GET all ratings
  fetch: thunk(async (actions, tmp) => {
    await api
      .url('/products/' + tmp[0] + '/ratings?ratingArea=' + tmp[1].toUpperCase())
      .get()
      .json((json) => actions.set(json))
      .catch(console.error);
  }),

  sendUpdate: thunk(async (actions, product) => {
    //console.log('send UPDATE project:', { projectData });
    actions.set(product);
    await api
      .url('/products/' + product.productID + '/ratings')
      .put(product)
      .res(console.log)
      .catch(console.error);

    actions.set(product);
  }),
};

const store = createStore({
  projectList: projectListModel,
  project: projectModel,
  rating: ratingModel,
  productList: productAreaModel,
  resultList: resultModel,
  product_rating: product_rating,
});

export default store;
