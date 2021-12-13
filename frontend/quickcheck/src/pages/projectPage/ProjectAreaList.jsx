import { React } from 'react';
import { Button, Heading, IconButton, Spacer, List, Flex } from '@chakra-ui/react';
import { DeleteIcon } from '@chakra-ui/icons';
import { useStoreActions, useStoreState } from 'easy-peasy';

import ConfirmClick from '../../components/ConfirmClick.jsx';

import AddAreaButton from './AddAreaButton.jsx';

function ProductArea({ productArea }) {
  return (
    <>
      <Button as="a" variant="secondary" rounded="md" href="/projects" w="full" py={5}>
        <Spacer />
        <Heading size="md" align="center">
          {productArea.name}
        </Heading>
        <Spacer />
      </Button>
    </>
  );
}

const RemoveButton = ({ handleRemove, ...buttonProps }) => {
  return (
    <ConfirmClick onConfirm={handleRemove} confirmPrompt="Remove this product area?">
      <IconButton icon={<DeleteIcon />} {...buttonProps} />
    </ConfirmClick>
  );
};

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
          <Flex gridGap={4} w="full" align="center">
            <ProductArea key={id} productArea={fetchArea(id)} />
            {editMode ? (
              <ConfirmClick
                onConfirm={() => removeProductArea(id)}
                confirmPrompt="Remove this product area?"
              >
                <IconButton icon={<DeleteIcon />} variant="whisper" size="lg" w={20} />
              </ConfirmClick>
            ) : undefined}
          </Flex>
        ))}
        {editMode ? (
          <AddAreaButton variant="primary" size="lg" w={20} onAdd={addProductArea}></AddAreaButton>
        ) : (
          <div />
        )}
      </List>
    </>
  );
}
