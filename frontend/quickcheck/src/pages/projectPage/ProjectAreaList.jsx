import { React } from 'react';
import { Button, Heading, IconButton, Spacer, List, Flex } from '@chakra-ui/react';
import { DeleteIcon } from '@chakra-ui/icons';
import { useStoreActions, useStoreState } from 'easy-peasy';

import ConfirmClick from '../../components/ConfirmClick.jsx';

import AddAreaButton from './AddAreaButton.jsx';

function ProductArea({ productArea }) {
  return (
    <>
      <Button as="a" variant="secondary" rounded="3xl" href="/projects" w="full" py={9}>
        <Spacer />
        <Heading size="lg" align="center">
          {productArea.name}
        </Heading>
        <Spacer />
      </Button>
    </>
  );
}

const areaMock = {
  0: { id: 0, name: 'Kredit', category: 'Privat' },
  1: { id: 1, name: 'Kunden', category: 'Business' },
  2: { id: 2, name: 'Payments', category: 'Privat' },
};

const fetchArea = (id) => {
  return areaMock[id];
};

export default function ProductAreaList({ editMode }) {
  const productAreas = useStoreState((state) => state.project.data.productAreas);
  const addProductArea = useStoreActions((actions) => actions.project.addProductArea);
  const removeProductArea = useStoreActions((actions) => actions.project.removeProductArea);

  return (
    <>
      <List w="full" maxW={500} align="center" spacing={4} pb={5}>
        {productAreas.map((id) => (
          <Flex gridGap={2} w="full" align="center" key={id}>
            <ProductArea key={id} productArea={fetchArea(id)} />
            {editMode ? (
              <ConfirmClick
                onConfirm={() => removeProductArea(id)}
                confirmPrompt="Remove this product area?"
              >
                <IconButton icon={<DeleteIcon />} variant="whisper" size="lg" />
              </ConfirmClick>
            ) : undefined}
          </Flex>
        ))}
        {editMode ? (
          <AddAreaButton variant="primary" size="lg" w={32} onAdd={addProductArea}></AddAreaButton>
        ) : (
          <div />
        )}
      </List>
    </>
  );
}
