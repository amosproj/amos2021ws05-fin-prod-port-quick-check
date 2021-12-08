import { React, useState } from 'react';
import {
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalBody,
  ModalFooter,
  ModalCloseButton,
  ModalHeader,
  Button,
  Flex,
  HStack,
  Heading,
  IconButton,
  Spacer,
  List,
} from '@chakra-ui/react';
import { AddIcon, DeleteIcon } from '@chakra-ui/icons';
import { Selection } from './Selection.jsx';
import ConfirmClick from './ConfirmClick.jsx';
import Card from './Card.jsx';

function AddButton({ onAdd }) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const allAreas = fetchAllAreas();

  const existingAreas = []; // mock. use state management

  const [selectedArea, setSelectedArea] = useState();

  const header = 'Add Product Area';

  const getAreaFromName = (areaName) => {
    return allAreas.filter((m) => m.name === areaName)[0];
  };

  return (
    <>
      <IconButton
        icon={<AddIcon />}
        aria-label="Add Product Area"
        onClick={onOpen}
        variant="primary"
        size="lg"
        w={16}
      ></IconButton>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader color="primary">{header}</ModalHeader>
          <ModalCloseButton />

          <ModalBody px={10}>
            <Selection
              placeholder="Select Poduct Area..."
              options={allAreas
                .filter((area) => !existingAreas.includes(area.id)) // filter out areas that already exist
                .map((e) => e.name)}
              onChange={(e) => setSelectedArea(e.target.value)}
            />
          </ModalBody>

          <ModalFooter>
            <Button
              variant="primary"
              mr={3}
              disabled={selectedArea === undefined}
              onClick={(e) => {
                onAdd(getAreaFromName(selectedArea).id);
                onClose();
              }}
            >
              Save
            </Button>
            <Button onClick={onClose} variant="whisper">
              Cancel
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}

function ProductArea({ productArea, removeButton }) {
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

const RemoveButton = ({ handleRemove, ...rest }) => {
  return (
    <ConfirmClick onConfirm={handleRemove} confirmPrompt="Remove this product area?">
      <IconButton icon={<DeleteIcon />} {...rest} />
    </ConfirmClick>
  );
};

const areaMock = {
  0: { id: 0, name: 'Kredit', category: 'Privat' },
  1: { id: 1, name: 'Kunden', category: 'Business' },
  2: { id: 2, name: 'Payments', category: 'Privat' },
};

const fetchAllAreas = () => {
  return Object.values(areaMock);
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
      <List w="50%" align="center" spacing={4} pb={5}>
        {areaIDs.map((id) => (
          <Card gridGap={5} w="full">
            <ProductArea key={id} productArea={fetchArea(id)} />
            {editMode ? (
              <RemoveButton variant='whisper' size='lg' handleRemove={handleRemoveArea(id)}></RemoveButton>
            ) : undefined}
          </Card>
        ))}
        {editMode ? <AddButton onAdd={handleAddArea}></AddButton> : <div />}
      </List>
    </>
  );
}
