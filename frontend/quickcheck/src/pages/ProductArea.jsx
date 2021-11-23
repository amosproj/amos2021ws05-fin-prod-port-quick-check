import { List, VStack } from '@chakra-ui/layout';
import React from 'react';
import ProductElement from '../components/ProductElement';

// beispiel mock für eine produkt, das wird dann als prop an eine produktzeile übergeben
const mockProduct = {
  name: 'Dispokredit',
  rating: {
    economic: 0.3,
    complexity: 0.8, // erstmal nur als progress anzeige 0.8 = 80% fortschritt
  },
  remark: 'bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla',
};

function ProductArea(props) {
  return (
    <div>
      {/* Menubar hier mit namen von produktarea (wird in den props )*/}
      <VStack justifyContent="center" spacing={10} mt={5}>
        {/* vielleicht ein header mit dem namen "Products", 
                daneben ein button "add product" oder "edit products" */}

        <List>
          {/* liste von Produktelementen (siehe components) */}
          <ProductElement product={mockProduct} />
        </List>
      </VStack>
    </div>
  );
}

export default ProductArea;
