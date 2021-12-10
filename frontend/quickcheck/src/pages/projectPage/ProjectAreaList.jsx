import { React } from 'react';
import { Button, Heading, IconButton, Spacer, List, Flex } from '@chakra-ui/react';
import { DeleteIcon } from '@chakra-ui/icons';

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

export default function ProductAreaList({ editMode, areaIDs, handleChange }) {
  const fetchArea = (areaID) => {
    return areaMock[areaID];
  };

  const handleAddArea = (newID) => {
    handleChange([...areaIDs, newID]);
  };

  const handleRemoveArea = (removeID) => () => {
    const updatedAreaIDs = areaIDs.filter((m) => m !== removeID);
    handleChange(updatedAreaIDs);
  };

  return (
    <>
      <List w="full" maxW={500} align="center" spacing={4} pb={5}>
        {areaIDs.map((id) => (
          <Flex gridGap={2} w="full" align="center">
            <ProductArea key={id} productArea={fetchArea(id)} />
            {editMode ? (
              <RemoveButton variant="whisper" size="lg" handleRemove={handleRemoveArea(id)} />
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
