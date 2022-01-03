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
  IconButton,
} from '@chakra-ui/react';
import { AddIcon } from '@chakra-ui/icons';
import { useStoreState, useStoreActions } from 'easy-peasy';

import Selection from '../../components/Selection.jsx';

const areaMock = {
  0: { id: 0, name: 'Kredit', category: 'Privat' },
  1: { id: 1, name: 'Kunden', category: 'Business' },
  2: { id: 2, name: 'Payments', category: 'Privat' },
};

const fetchAllAreas = () => {
  return Object.values(areaMock);
};

export default function AddAreaButton(buttonProps) {
  const productAreas = useStoreState((state) => state.project.data.productAreas);
  const addProductArea = useStoreActions((actions) => actions.project.addProductArea);

  const { isOpen, onOpen, onClose } = useDisclosure();
  const allAreas = fetchAllAreas();
  const [selectedArea, setSelectedArea] = useState();
  const header = 'Add Product Area';

  const fetchArea = () => {
    // todo: implement fetching possible Area choices
  };

  return (
    <>
      <IconButton
        icon={<AddIcon />}
        aria-label="Add Product Area"
        onClick={onOpen}
        {...buttonProps}
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
                .filter((area) => !productAreas.map((a) => a.id).includes(area.id)) // lookup if area already added // This removes the wrong product area
                .map((e) => e.name)} // display name in selection field
              onChange={setSelectedArea}
            />
          </ModalBody>

          <ModalFooter>
            <Button
              variant="primary"
              mr={3}
              disabled={selectedArea === undefined}
              onClick={(e) => {
                addProductArea({
                  key: productAreas.length,
                  id: selectedArea.id,
                  name: selectedArea,
                  category: 'Private',
                });
                console.log(selectedArea);
                onClose();
                console.log(productAreas);
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
