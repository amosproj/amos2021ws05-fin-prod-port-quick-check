import { HStack } from '@chakra-ui/layout';
import React from 'react';

function ProductElement(props) {
  return (
    <HStack bg="gray.600">
      {/* das hier ist eine zeile in der produkt liste, jede zeile hat folgende felder: 
                - label mit produktname (props.product.name)
                - button für wirtschaftliche bewertung. 
                    als button text vielleicht den bewertungsfortschritt (zb. props.product.rating.economic)
                - button für komplexitätsbewertung
                - textfeld für anmerkungen
                - button für nachweise mit text "upload" (vielleicht iconbutton mit schönem icon :) 
            */}
    </HStack>
  );
}

export default ProductElement;
