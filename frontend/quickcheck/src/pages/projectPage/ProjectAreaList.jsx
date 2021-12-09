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

export default function ProductAreaList({ editMode, handleChange }) {


  
  const productAreas = useStoreState((state) => state.project.productAreas);
  const updateProject = useStoreActions((actions) => actions.updateProject);
  const handleUpdateAreas = (productAreas) => updateProject({productAreas: productAreas})

  const fetchArea = (areaID) => {
    return areaMock[areaID];
  };

  const handleAddArea = (newID) => {
    handleUpdateAreas([...productAreas, newID]);
  };

  const handleRemoveArea = (removeID) => () => {
    const updatedAreaIDs = productAreas.filter((m) => m !== removeID);
    handleUpdateAreas(updatedAreaIDs);
  };

  return (
    <>
      <List w="full" maxW={500} align="center" spacing={4} pb={5}>
        {productAreas.map((id) => (
          <Flex gridGap={2} w="full" align="center">
            <ProductArea key={id} productArea={fetchArea(id)} />
            {editMode ? (
              // <RemoveButton variant="whisper" size="lg" handleRemove={handleRemoveArea(id)} />
              <ConfirmClick onConfirm={handleRemoveArea(id)} confirmPrompt="Remove this product area?">
              <IconButton icon={<DeleteIcon />} variant="whisper" size="lg" />
            </ConfirmClick>
            ) : undefined}
          </Flex>
        ))}
        {editMode ? (
          <AddAreaButton variant="primary" size="lg" w={32} onAdd={handleAddArea}></AddAreaButton>
        ) : (
          <div />
        )}
      </List>
    </>
  );
}
